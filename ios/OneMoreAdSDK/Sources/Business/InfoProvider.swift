//
//  InfoProvider.swift
//  OneMoreAdSDK
//
//  Created by admin on 12/24/19.
//  Copyright © 2019 SLG. All rights reserved.
//

import Foundation
import UIKit
import AdSupport

struct InfoProvider {
    
    // MARK: Private
    private let webViewFrame: CGRect?
    
    // MARK: Internal
    let adIdentifier: String = ASIdentifierManager.shared().advertisingIdentifier.uuidString
    let deviceProvider: String = "Apple"
    let systemName: String = Device.current.systemName ?? "null"
    let systemVersion: String = Device.current.systemVersion ?? "null"
    let model: String = Device.current.model ?? "null"
    let fullModelName: String = Device.current.realDevice.description
    let bundleID: String = Bundle.main.bundleIdentifier ?? "null"
    let processorCount = ProcessInfo.processInfo.processorCount
    let physicalMemory = ProcessInfo.processInfo.physicalMemory
    let languageCode: String = Locale.current.languageCode ?? "null"
    
    // MARK: Computed
    var screen: String { "\(Int(UIScreen.main.bounds.width))x\(Int(UIScreen.main.bounds.height))" }
    var timeZoneShift: Int { TimeZone.current.secondsFromGMT() / 60 }
    var webViewWidth: Int { Int(webViewFrame?.size.width ?? 0) }
    var webViewHeight: Int { Int(webViewFrame?.size.height ?? 0) }
    var userID: String { InfoStorage.storedUserId ?? "" }
    var id: String { InfoStorage.storedId ?? "" }
    var advToken: String { InfoStorage.storedAdvToken ?? "" }
    var appName: String { InfoStorage.storedAppName ?? "" }
    
    // MARK: Init
    init(webViewFrame: CGRect? = nil) {
        self.webViewFrame = webViewFrame
    }
    
//    private static func secondsToHoursMinutesSeconds(seconds : Int) -> (Int, Int, Int) {
//      return (seconds / 3600, (seconds % 3600) / 60, (seconds % 3600) % 60)
//    }
}
