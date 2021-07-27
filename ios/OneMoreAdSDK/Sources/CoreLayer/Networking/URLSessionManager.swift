//
//  URLSessionMananger.swift
//  OneMoreAdSDK
//
//  Created by admin on 8/22/19.
//  Copyright © 2019 SLG. All rights reserved.
//

import Foundation

struct SessionManagerResponse {
    let result: Result<(Data?, HTTPURLResponse), URLSessionError>
    let request: URLRequest
}

class URLSessionManager {
    
    let session: URLSession
    
    init(configuration: URLSessionConfiguration = URLSessionConfiguration.default) {
        session = URLSession(configuration: configuration)
    }
    
    @discardableResult
    func perform(request: URLRequest,
                        completion: @escaping (SessionManagerResponse) -> Void) -> URLSessionDataTask {
        
        let dataTask = session.dataTask(with: request) { data, response, error in
            let sessionManagerResponse: SessionManagerResponse
            if let response = response as? HTTPURLResponse {
                sessionManagerResponse = .init(
                    result: .success((data, response)),
                    request: request)
            } else {
                sessionManagerResponse = .init(
                    result: .failure(URLSessionError(urlError: error)),
                    request: request)
            }
            completion(sessionManagerResponse)
        }
        dataTask.resume()
        
        return dataTask
    }
}
