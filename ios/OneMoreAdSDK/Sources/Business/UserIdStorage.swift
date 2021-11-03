

import Foundation

class UserIdStorage {
    
    enum C {
        static let suiteName = "OneMoreAdSDK.UserDefaultsSuite"
        static let key = "OneMoreAdSDK.userID"
    }
    
    static var userId: String? {
        guard
            let userDefaults = UserDefaults(suiteName: C.suiteName),
            let userId = userDefaults.value(forKey: C.key) as? String else {
            return nil
        }
        return userId
    }
    
    static func setUserId(_ userId: String?) {
        guard let userDefaults = UserDefaults(suiteName: C.suiteName)else {
            return
        }
        
        userDefaults.set(userId, forKey: C.key)
        userDefaults.synchronize()
    }
    
}
