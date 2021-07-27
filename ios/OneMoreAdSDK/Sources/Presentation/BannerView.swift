//
//  BannerView.swift
//  OneMoreAdSDK
//
//  Created by admin on 12/24/19.
//  Copyright © 2019 SLG. All rights reserved.
//

import UIKit
import WebKit

public class BannerView: UIView {
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        commonInit()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        commonInit()
    }
    
    private func commonInit() {
        addSubview(webViewContainer)
        webViewContainer.frame = CGRect(x: 0, y: 0, width: frame.size.width, height: frame.size.height)
    }
    
    private lazy var webViewContainer: OneMoreAdSDKWebViewContainer = {
        let webViewContainer = OneMoreAdSDKWebViewContainer(frame: .zero)
        webViewContainer.mediaCompleteCallback = {
            webViewContainer.isHidden = true
        }
        return webViewContainer
    }()
    
    // MARK: - Interface
    func loadScript(_ script: String) {
        webViewContainer.loadScript(script)
    }
}
