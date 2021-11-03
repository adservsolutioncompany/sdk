//
//  ViewController.swift
//  OneMoreAdSDKDemo
//
//  Created by admin on 12/26/19.
//  Copyright © 2019 SLG. All rights reserved.
//

import UIKit
import OneMoreAdSDK

class ViewController: UITableViewController {
    
    var bannerView: UIView?
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        switch (indexPath.section, indexPath.row) {
        case(0, 0):
            OneMoreAd.default.loadInterstitial(on: self) { (result) in }
        case (0, 1):
            OneMoreAd.default.loadBanner(
                on: self,
                targetSize: tableView.tableFooterView!.frame.size,
                bannerType: .bannerLarge
            ) { [weak self] (result) in
                guard let self = self, case .success(let bannerView) = result else { return }
                self.showBanner(bannerView)
            }
        case (0, 2):
            OneMoreAd.default.loadVideo(on: self) { (result) in }
        case(1, 0):
            OneMoreAd.default.trackReg()
        case(1, 1):
            OneMoreAd.default.trackAuth()
        case(1, 2):
            OneMoreAd.default.trackBet()
        case(1, 3):
            OneMoreAd.default.trackDep()
        default:
            break
        }
        
        tableView.deselectRow(at: indexPath, animated: true)
    }
    
    func showBanner(_ bannerView: UIView) {
        guard let superView = self.tableView.tableFooterView else { return }
        self.bannerView?.removeFromSuperview()
        superView.addSubview(bannerView)
        self.bannerView = bannerView
    }
}
