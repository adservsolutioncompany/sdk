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
    
    override func viewDidLoad() {
        super.viewDidLoad()
        OneMoreAd.default.configure(domain: "z.cdn.adtarget.me", id: "1466054648")
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        switch indexPath.row {
        case 0:
            OneMoreAd.default.loadInterstitial(on: self) { (result) in }
        case 1:
            OneMoreAd.default.loadBanner(
                on: self,
                targetSize: tableView.tableFooterView!.frame.size,
                bannerType: .bannerLarge
            ) { [weak self] (result) in
                guard let self = self, case .success(let bannerView) = result else { return }
                self.showBanner(bannerView)
            }
        case 2:
            OneMoreAd.default.loadVideo(on: self) { (result) in }
        default:
            break
        }
    }
    
    func showBanner(_ bannerView: UIView) {
        guard let superView = self.tableView.tableFooterView else { return }
        self.bannerView?.removeFromSuperview()
        superView.addSubview(bannerView)
        self.bannerView = bannerView
    }
}
