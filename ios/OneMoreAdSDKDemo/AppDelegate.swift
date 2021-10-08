//
//  AppDelegate.swift
//  OneMoreAdSDKDemo
//
//  Created by admin on 12/24/19.
//  Copyright © 2019 SLG. All rights reserved.
//

import UIKit
import OneMoreAdSDK

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?
    
    func application(_ application: UIApplication,
                     didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?)
    -> Bool {
        
        OneMoreAd.default.configure(
            domain: "z.cdn.advzone.net",
            id: "1991433590",
            advToken: "79IbvxRySnw-MHctQsxuaXQy0_jYqcZuLVgLf7aqEM0Ck9ZkJGQrUoFf0lnQS-9l",
            appName: "SomeApp",
            events: [
                .install(payload: .init(id:1, aud: 160)),
                .open(payload: .init(id:2, aud: 150)),
                .reg(payload: .init(id:3, aud: 140)),
                .auth(payload: .init(id:4, aud: 130)),
                .bet(payload: .init(id:5, aud: 120)),
                .dep(payload: .init(id:6, aud: 110))
            ]
        )
        
        return true
    }

}

