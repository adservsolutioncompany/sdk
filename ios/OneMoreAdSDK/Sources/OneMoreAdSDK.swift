//
//  OneMoreAdSDK.swift
//  OneMoreAdSDK
//
//  Created by admin on 12/24/19.
//  Copyright © 2019 SLG. All rights reserved.
//

import Foundation
import UIKit

public typealias InterstitialLoadResult = (Result<Void, OneMoreAdSDKError>) -> Void
public typealias BannerLoadResult = (Result<BannerView, OneMoreAdSDKError>) -> Void

public class OneMoreAd {
    
    public static let `default`: OneMoreAd = {
        return OneMoreAd()
    }()
    
    public func configure(domain: String, id: String) {
        self.domain = domain
        self.id = id
        client = Client(domain: domain, id: id)
    }
    
    // MARK: - Interstitials
    
    public func loadInterstitial(on viewController: UIViewController, completion: @escaping InterstitialLoadResult) {
        
        guard viewController.isViewLoaded == true else {
            completion(.failure(.parentViewControllerNotInViewHierarchy))
            return
        }
        
        guard let client = client else {
            completion(.failure(.sDKDoesNotConfigured))
            return
        }
        
        client.adContent(
            adType: .interstitial,
            analytics: AnalyticsProvider(
                webViewFrame: viewController.view.safeAreaLayoutGuide.layoutFrame
            )
        ) { [weak self, weak viewController] (result) in
            switch result {
            case .success(let script):
                if let self = self, let viewController = viewController {
                    self.presentFullScreenAd(
                        on: viewController,
                        script: script,
                        animated: true,
                        adType: .interstitial,
                        completion: completion
                    )
                }
                
            case .failure(let error):
                completion(.failure(.networkError(error: error)))
            }
        }
    }
    
    // MARK: - Banners
    public func loadBanner(on viewController: UIViewController,
                           targetSize: CGSize,
                           bannerType: BannerType,
                           completion: @escaping BannerLoadResult) {
        
        guard viewController.isViewLoaded == true else {
            completion(.failure(.parentViewControllerNotInViewHierarchy))
            return
        }
        
        guard let client = client else {
            completion(.failure(.sDKDoesNotConfigured))
            return
        }
        
        client.adContent(
            adType: bannerType.adType,
            analytics: AnalyticsProvider(
                webViewFrame: viewController.view.safeAreaLayoutGuide.layoutFrame
            )
        ) { [weak self] (result) in
            switch result {
            case .success(let script):
                self?.makeBanner(with: script, targetSize: targetSize, completion: completion)
            case .failure(let error):
                completion(.failure(.networkError(error: error)))
            }
        }
    }
    
    public func loadVideo(on viewController: UIViewController, completion: @escaping InterstitialLoadResult) {
        
        guard viewController.isViewLoaded == true else {
            completion(.failure(.parentViewControllerNotInViewHierarchy))
            return
        }
        
        guard let client = client else {
            completion(.failure(.sDKDoesNotConfigured))
            return
        }
        
        client.adContent(
            adType: .video,
            analytics: AnalyticsProvider(
                webViewFrame: viewController.view.safeAreaLayoutGuide.layoutFrame
            )
        ) { [weak self, weak viewController] (result) in
            switch result {
            case .success(let script):
                if let self = self, let viewController = viewController {
                    self.presentFullScreenAd(
                        on: viewController,
                        script: script,
                        animated: true,
                        adType: .video,
                        completion: completion
                    )
                }
                
            case .failure(let error):
                completion(.failure(.networkError(error: error)))
            }
        }
    }
    
    // MARK: - Private
    private var domain: String?
    private var id: String?
    private var client: Client?
    
    private func presentFullScreenAd(on viewController: UIViewController,
                                       script: String,
                                       animated: Bool,
                                       adType: AdType,
                                       completion: @escaping InterstitialLoadResult) {
        DispatchQueue.main.async {
            let interstitialVC = InterstitialViewController()
            interstitialVC.inject(dependencies: .init(adType: adType))
            interstitialVC.modalPresentationStyle = .overFullScreen
            interstitialVC.loadScript(script)
            viewController.present(interstitialVC, animated: animated)
            completion(.success(()))
        }
    }
    
    private func makeBanner(with script: String,
                            targetSize: CGSize,
                            completion: @escaping BannerLoadResult) {
        DispatchQueue.main.async {
            let frame = CGRect(x: 0, y: 0, width: targetSize.width, height: targetSize.height)
            let bannerView = BannerView(frame: frame)
            bannerView.loadScript(script)
            completion(.success(bannerView))
        }
    }
}
