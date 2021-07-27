//
//  InterstitialViewController.swift
//  OneMoreAdSDK
//
//  Created by admin on 12/24/19.
//  Copyright © 2019 SLG. All rights reserved.
//

import UIKit
import WebKit

class InterstitialViewController: UIViewController, WKScriptMessageHandler {

    // MARK: - Interface
    
    func inject(dependencies: Dependencies) {
        adType = dependencies.adType
    }

    struct Dependencies {
        let adType: AdType
    }
    
    // MARK: - Private
    private var script: String?
    private var adType: AdType?
    
    // MARK: - WKScriptMessageHandler
    func userContentController(_ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
        if let contentBody = message.body as? String, contentBody == "mediaComplete" {
            DispatchQueue.main.async {
                self.dismiss(animated: true, completion: nil)
            }
        }
    }
    
    override func loadView() {
        let webViewContainer = OneMoreAdSDKWebViewContainer()
        webViewContainer.mediaCompleteCallback = {
            DispatchQueue.main.async {
                self.dismiss(animated: true, completion: nil)
            }
        }
        
        view = webViewContainer
        
    }
    
    // MARK: - Interface
    func loadScript(_ script: String) {
        self.script = script
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        if let view = view as? OneMoreAdSDKWebViewContainer, let script = script {
            view.loadScript(script)
            
            if case .video = adType {
                view.webView?.isOpaque = false
                view.webView?.backgroundColor = .clear
                view.backgroundColor = UIColor.gray.withAlphaComponent(0.5)
            } else {
                view.backgroundColor = .white
            }
        }
    }
}
