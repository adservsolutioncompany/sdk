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
                  sessionManager: URLSessionManager = URLSessionManager(configuration: URLSessionConfiguration.default),
                  queue: DispatchQueue = DispatchQueue(label: "slg.OneMoreAdSDK.Network.Client." + UUID().uuidString)) {
        self.sessionManager = sessionManager
        self.queue = queue
        self.domain = domain
    }
    
    
    let sessionManager: URLSessionManager
    let queue: DispatchQueue
    let domain: String
    
    @discardableResult
    func perform(_ requestConvertible: URLRequestConvertible,
                 completion: @escaping (Result<String, ClientError>) -> Void) -> URLSessionDataTask? {
        do {
            let request = try requestConvertible.asURLRequest()
            print(request.url?.absoluteString)
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
    
    func verifyServerResponse(_ response: (data: Data?, urlResponse: HTTPURLResponse))
    -> Result<Data?, ClientError> {
        if (200..<300).contains(response.urlResponse.statusCode) {
            return .success(response.0)
        } else {
            return .failure(.serverError(statusCode: response.urlResponse.statusCode))
        }
    }
    
    func parseResult(_ data: Data?) -> Result<String, ClientError> {
        guard let data = data else {
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
                   infoProvider: InfoProvider,
                   completion: @escaping (Result<String, ClientError>) -> Void) -> URLSessionDataTask? {
        let queryItems: HTTPQueryItems = [
            "s": infoProvider.id,
            "t": adType.rawValue,
            "os": String(describing: infoProvider.systemName),
            "osv": String(describing: infoProvider.systemVersion),
            "model": String(describing: infoProvider.model),
            "hwv": String(describing: infoProvider.fullModelName),
            "sr": infoProvider.screen ,
            "app": String(describing: infoProvider.bundleID),
            "hc": String(describing: infoProvider.processorCount),
            "mem": String(describing: infoProvider.physicalMemory),
            "ln": String(describing: infoProvider.languageCode),
            "tz": String(describing: infoProvider.timeZoneShift),
            "did": infoProvider.adIdentifier,
            "cw": String(describing: infoProvider.webViewWidth),
            "ch": String(describing: infoProvider.webViewHeight)
        ]
        let target = Endpoint.ad(host: domain, queryItems: queryItems)
        return perform(target, completion: completion)
    }
    
    @discardableResult
    func requestUserID(infoProvider: InfoProvider,
                       completion: @escaping (Result<String, ClientError>) -> Void) -> URLSessionDataTask? {
        let queryItems: HTTPQueryItems = [
            "os": infoProvider.systemName,
            "osv": infoProvider.systemVersion,
            "make": infoProvider.deviceProvider,
            "model": infoProvider.model,
            "hwv": infoProvider.fullModelName,
            "sr": infoProvider.screen,
            "hc": infoProvider.processorCount.description,
            "mem": infoProvider.physicalMemory.description,
            "ln": infoProvider.languageCode,
            "tz": infoProvider.timeZoneShift.description,
            "did": infoProvider.adIdentifier
        ]
        let target = Endpoint.userId(host: domain, queryItems: queryItems)
        return perform(target, completion: completion)
    }
    
    @discardableResult
    func trackEvent(_ event: Event,
                    infoProvider: InfoProvider,
                    completion: @escaping (Result<String, ClientError>) -> Void) -> URLSessionDataTask? {
        let queryItems: HTTPQueryItems = [
            "token": infoProvider.advToken,
            "app": infoProvider.appName,
            "e": event.id,
            "aud": event.aud,
            "user": infoProvider.userID
        ]
        let target = Endpoint.install(host: domain, queryItems: queryItems)
        return perform(target, completion: completion)
    }
}
