//
//  Client.swift
//  OneMoreAdSDK
//
//  Created by admin on 8/22/19.
//  Copyright © 2019 SLG. All rights reserved.
//

import Foundation

class Client {
    
    required init(domain: String,
                  id: String,
                  sessionManager: URLSessionManager = URLSessionManager(configuration: URLSessionConfiguration.default),
                  queue: DispatchQueue = DispatchQueue(label: "slg.OneMoreAdSDK.Network.Client." + UUID().uuidString)) {
        self.sessionManager = sessionManager
        self.queue = queue
        self.domain = domain
        self.id = id
    }
    
    
    let sessionManager: URLSessionManager
    let queue: DispatchQueue
    let domain: String
    let id: String
    
    @discardableResult
    func perform(_ requestConvertible: URLRequestConvertible,
                 completion: @escaping (Result<String, ClientError>) -> Void) -> URLSessionDataTask? {
        do {
            let request = try requestConvertible.asURLRequest()
            return sessionManager.perform(request: request) { [weak self] (response) in
                guard let self = self else { return }
                self.queue.async {
                    let result: Result<String, ClientError> = response.result
                        .flatMap(ifSuccess: self.verifyServerResponse, ifFailure: self.networkErrorToResult)
                        .flatMap(ifSuccess: self.parseResult, ifFailure: liftError)
                    
                    completion(result)
                }
            }
        } catch {
            completion(.failure(.endpointError(error: error)))
            return nil
        }
    }
    
    func verifyServerResponse(_ response: (data: Data?, urlResponse: HTTPURLResponse)) -> Result<Data?, ClientError> {
        if (200..<300).contains(response.urlResponse.statusCode) {
            return .success(response.0)
        } else {
            return .failure(.serverError(statusCode: response.urlResponse.statusCode))
        }
    }
    
    func parseResult(_ data: Data?) -> Result<String, ClientError> {
        guard let data = data, !data.isEmpty else {
            return .failure(.dataIsEmptyError)
        }
        
        let decodedData = String(data: data, encoding: .utf8)
        if let decodedData = decodedData {
            return .success(decodedData)
        } else {
            return .failure(.parsingError)
        }
    }
    
    func networkErrorToResult(_ error: URLSessionError) -> Result<Data?, ClientError> {
        return .failure(.networkError(error: error))
    }
}

// MARK: - Concrete methods
extension Client {
    
    // http://z.cdn.adtarget.me/app?s=1466054648&t=bannerRectangle&cw=300&ch=250&sr=1920x1080
    @discardableResult
    func adContent(adType: AdType,
                   analytics: AnalyticsProvider,
                   completion: @escaping (Result<String, ClientError>) -> Void) -> URLSessionDataTask? {
        let queryItems: HTTPQueryItems = [
            "s": id,
            "t": adType.rawValue,
            "os": String(describing: analytics.systemName),
            "osv": String(describing: analytics.systemVersion),
            "model": String(describing: analytics.model),
            "hwv": String(describing: analytics.fullModelName),
            "sr": analytics.screen ,
            "app": String(describing: analytics.bundleID),
            "hc": String(describing: analytics.processorCount),
            "mem": String(describing: analytics.physicalMemory),
            "ln": String(describing: analytics.languageCode),
            "tz": String(describing: analytics.timeZoneShift),
            "did": analytics.adIdentifier,
            "cw": String(describing: analytics.webViewWidth),
            "ch": String(describing: analytics.webViewHeight)
        ]
        let target = Endpoint.ad(host: domain, queryItems: queryItems)
        return perform(target, completion: completion)
    }
}
