//  Copyright © 2021 SLG. All rights reserved.

import Foundation

struct InfoStorage {
    // MARK: Static
    static var storedUserId: String?
    {
        get {
            UserIdStorage.userId
        }
        set {
            UserIdStorage.setUserId(newValue)
        }
    }
    static var storedId: String?
    static var storedAdvToken: String?
    static var storedAppName: String?
}
