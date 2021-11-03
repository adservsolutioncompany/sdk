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
    
    // MARK: Private
    private var client: Client?
    private var availableEvents: [Event] = []
    
    private let userDefaults = UserDefaults(suiteName: "slg.OneMoreAdSDK")
    private let isAppInstalledKey = "iSAppIstalledKey"
    var isAppInstalled: Bool {
        guard let userDefaults = userDefaults else { return false }
        
        let isAppInstalledKey = userDefaults.bool(forKey: isAppInstalledKey)
        return isAppInstalledKey
    }
    
    // MARK: Public
    public static let `default`: OneMoreAd = {
        return OneMoreAd()
    }()
}

// MARK: - Public methods
// MARK: Configuration
public extension OneMoreAd {
    func configure(domain: String, id: String, advToken: String, appName: String, events: [Event]) {

        self.availableEvents = events
        
        InfoStorage.storedId = id
        InfoStorage.storedAppName = appName
        InfoStorage.storedAdvToken = advToken
        
        client = Client(domain: domain)
        
        trackNewConfiguration()
    }
}

// MARK: Events
public extension OneMoreAd {
    func trackReg(completion: (() -> Void)? = nil) {
        let regEvent = availableEvents.first(where: { event in
            guard case .reg = event else { return false }
            return true
        })
        
        trackEvent(regEvent)
    }
    
    func trackAuth(completion: (() -> Void)? = nil) {
        let authEvent = availableEvents.first(where: { event in
            guard case .auth = event else { return false }
            return true
        })
        
        trackEvent(authEvent)
    }
    
    func trackBet(completion: (() -> Void)? = nil) {
        let betEvent = availableEvents.first(where: { event in
            guard case .bet = event else { return false }
            return true
        })
        
        trackEvent(betEvent)
    }
    
    func trackDep(completion: (() -> Void)? = nil) {
        let depEvent = availableEvents.first(where: { event in
            guard case .dep = event else { return false }
            return true
        })
        
        trackEvent(depEvent)
    }
}

// MARK: Ads
public extension OneMoreAd {
    
    // MARK: Interstitials
    func loadInterstitial(on viewController: UIViewController,
                          completion: @escaping InterstitialLoadResult) {
        
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
            infoProvider: InfoProvider(
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
    
    // MARK: Banners
    func loadBanner(on viewController: UIViewController,
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
            infoProvider: InfoProvider(
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
    
    func loadVideo(on viewController: UIViewController, completion: @escaping InterstitialLoadResult) {
        
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
            infoProvider: InfoProvider(
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
}

// MARK: - Private methods
// MARK: Events
private extension OneMoreAd {
    func trackEvent(_ event: Event?, completion: (() -> Void)? = nil) {
        guard let event = event else {
            completion?()
            return
        }
        
        findUserId { [weak client] in
            guard let client = client else { return }
            client.trackEvent(
                event,
                infoProvider: InfoProvider(webViewFrame: nil)
            ) { result in
                completion?()
            }
        }
    }
    
    func trackInstall(completion: (() -> Void)? = nil) {
        let installEvent = availableEvents.first(where: { event in
            guard case .install = event else { return false }
            return true
        })
        
        trackEvent(installEvent)
    }
    
    func trackOpen(completion: (() -> Void)? = nil) {
        let openEvent = availableEvents.first(where: { event in
            guard case .open = event else { return false }
            return true
        })
        
        trackEvent(openEvent)
    }
}

// MARK: Ads
private extension OneMoreAd {
    func presentFullScreenAd(on viewController: UIViewController,
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
    
    func makeBanner(with script: String,
                    targetSize: CGSize,
                    completion: @escaping BannerLoadResult) {
        DispatchQueue.main.async {
            let frame = CGRect(x: 0, y: 0, width: targetSize.width, height: targetSize.height)
            let bannerView = BannerView(frame: frame)
            bannerView.loadScript(script)
            completion(.success(bannerView))
        }
    }
    
    func findUserId(completion: @escaping () -> Void) {
        
        if InfoStorage.storedUserId != nil {
            completion()
        }
        
        client?.requestUserID(
            infoProvider: InfoProvider()
        ) { result in
            let userId = try? result.get()
            InfoStorage.storedUserId = userId
            completion()
        }
    }
    
    func trackNewConfiguration() {
        if isAppInstalled {
            trackOpen()
        } else {
            trackInstall()
            userDefaults?.set(true, forKey: isAppInstalledKey)
        }
    }
}
