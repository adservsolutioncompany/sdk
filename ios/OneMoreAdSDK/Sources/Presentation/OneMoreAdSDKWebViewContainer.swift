//
//  OneMoreAdSDKWebViewContainer.swift
//  OneMoreAdSDK
//
//  Created by admin on 12/26/19.
//  Copyright © 2019 SLG. All rights reserved.
//

import UIKit
import WebKit

class OneMoreAdSDKWebViewContainer: UIView, WKNavigationDelegate, WKScriptMessageHandler {
    
    // MARK: - Interface
    
    var mediaCompleteCallback: (() -> Void)?
    
    // MARK: - Private
    private(set) var webView: WKWebView?
    
    private func setUpWebView() {
        let controller = WKUserContentController()
        controller.add(self, name: "adSDKHandler")
        let configuration = WKWebViewConfiguration()
        configuration.userContentController = controller
        configuration.mediaTypesRequiringUserActionForPlayback = []
        configuration.allowsInlineMediaPlayback = true
        let webView = WKWebView(frame: .zero, configuration: configuration)
        webView.navigationDelegate = self
        webView.scrollView.isScrollEnabled = false
        webView.translatesAutoresizingMaskIntoConstraints = false
        
        addSubview(webView)
        NSLayoutConstraint.activate([
            webView.leadingAnchor.constraint(equalTo: leadingAnchor),
            webView.trailingAnchor.constraint(equalTo: trailingAnchor),
            webView.topAnchor.constraint(equalTo: topAnchor),
            webView.bottomAnchor.constraint(equalTo: bottomAnchor)
        ])
        
        self.webView = webView
    }
    
    func loadScript(_ script: String) {
        if webView == nil {
            setUpWebView()
        }
        
        let html = """
        <html>
        <head>
        </head>
        <body>
        <script>
        \(script)
        </script>
        </body>
        </html>
        """
        
        webView?.loadHTMLString(html, baseURL: nil)
    }
    
    // MARK: - WKNavigationDelegate
    func webView(_ webView: WKWebView,
                 decidePolicyFor navigationAction: WKNavigationAction,
                 decisionHandler: @escaping (WKNavigationActionPolicy) -> Void) {
        guard case .linkActivated = navigationAction.navigationType, let url = navigationAction.request.url else {
            decisionHandler(.allow)
            return
        }
        decisionHandler(.cancel)
        UIApplication.shared.open(url)
    }
    
    // MARK: - WKScriptMessageHandler
    func userContentController(_ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
        if let contentBody = message.body as? String, contentBody == "mediaComplete" {
            DispatchQueue.main.async {
                self.mediaCompleteCallback?()
            }
        }
    }
}
