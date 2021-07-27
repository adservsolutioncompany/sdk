# iOS OneMoreSDK Integration
## Step 1. Add the SDK to Your Project
### Manual Download

1. Add OneMoreAdSDK.framework into your Xcode Project
2. Go to: Target -> General -> Frameworks, Libraries, and Embedded Content
3. Choose "Embed without Signing" for OneMoreAdSDK.framework

### App Transport Security Settings

Important! In iOS 9, Apple added in controls around ‘ATS’. To ensure uninterrupted support for ironSource ad delivery across all mediation networks, it’s important to make the following changes in your info.plist:

* Add in a dictionary called ‘NSAppTransportSecurity‘. Make sure you add this dictionary on the ‘Top Level Key‘.
* Inside this dictionary, add a Boolean called ‘NSAllowsArbitraryLoads‘ and set it to YES.

### Usage
#### Import

```swift
import OneMoreAdSDK
```

#### Configure SDK with specific domain and id

```swift
OneMoreAdSDK.default.configure(domain: "z.cdn.adtarget.me", id: "1466054648")
```

#### Interstitial

```swift
OneMoreAdSDK.default.loadInterstitial(on: self) { (result) in
            switch result {
            case .success:
                break
            case .failure(let error):
                break
            }
```

#### Banner

```swift
            OneMoreAdSDK.default.loadBanner(
                on: self,
                targetSize: tableView.tableFooterView!.frame.size,
                bannerType: .bannerLarge
            ) { [weak self] (result) in
                guard let self = self, case .success(let bannerView) = result else { return }
                self.showBanner(bannerView)
            }
```

```swift
func showBanner(_ bannerView: UIView) {
        guard let superView = self.tableView.tableFooterView else { return }
        self.bannerView?.removeFromSuperview()
        superView.addSubview(bannerView)
        self.bannerView = bannerView
    }
```

#### Video

```swift
OneMoreAdSDK.default.loadVideo(on: self) { (result) in }
```

