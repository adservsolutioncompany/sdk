//  Copyright © 2021 SLG. All rights reserved.

import Foundation

public enum Event {
    case install(payload: EventPayload)
    case open(payload: EventPayload)
    case reg(payload: EventPayload)
    case auth(payload: EventPayload)
    case bet(payload: EventPayload)
    case dep(payload: EventPayload)
    
    var name: String {
        switch self {
        case .install:
            return "install"
        case .reg:
            return "reg"
        case .open:
            return "open"
        case .auth:
            return "auth"
        case .bet:
            return "bet"
        case .dep:
            return "dep"
        }
    }
    
    var aud: String { payload.aud.description }
    
    var id: String { payload.id.description }
    
    private var payload: EventPayload {
        switch self {
        case .install(let payload),
             .reg(let payload),
             .open(let payload),
             .auth(let payload),
             .bet(let payload),
             .dep(let payload):
            return payload
        }
    }
}

public struct EventPayload {
    public let id: Int
    public let aud: Int
    
    public init(id: Int, aud: Int) {
        self.id = id
        self.aud = aud
    }
}
