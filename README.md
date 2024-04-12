

#### Settings for event transmission in the APP

Creating an APP, it needs to configure sending requests to
endpoints:   


Endpoint for receiving userID tracking.    
__[protocol]://[hostname]/appmc?token=[adv-token]&…__  
Method GET, timeout min 60sec    


Installing the application, the request is sent about the device for matching tracking 
user

* __ifa__ - ***string*** - required IDFA or Google Advertising ID
* __os__  - ***string*** - Device operating system (e.g., “iOS”)
* __osv__ - ***string*** - OS version
* __make__ - ***string*** - Device make (e.g., “Apple”)
* __model__ - ***string*** - Device model (e.g., “iPhone”).
* __hwv__ - ***string*** - Hardware version of the device (e.g., “5S” for iPhone 5S).
* __sr__ - ***string*** - Physical width x height of the screen in pixels. (e.g., "455x960")
* __hc__ - ***int*** - Number of CPU cores.
* __mem__ - ***int*** - Total RAM of device in Gb (e.g., 32)
* __ln__ - ***string*** - Browser language using ISO-639-1:2002 2 letter (e.g., "ua").
* __tz__ - ***int*** - Local time as the number +/- of minutes from UTC.
* __did__ - ***string*** - Device ID  

In the response, the tracker will send the userID that the application stores in its storage.  
If the application did not receive or lost the userID, then it can re-apply
send a request    


Endpoint for receiving userID tracking.    
__[protocol]://[hostname]/appevent?token=[adv-token]&app=[app-name]&e=[event-id]&user=[user-id]__    
Method GET    
When an event occurs, the application sends a request to the tracker. [event-id] should
match those created in the tracker.    

__[protocol]__ - http | https. The tracker supports both but at high RPS. It is preferable to use http  

__[hostname]__ - hostname of the tracker  

__[adv-token]__ - advertiser token  

__[event-id]__ - events to be tracked and their id. Example:  

* install - “id”: 1
* reg  - “id”: 2
* auth  - “id”: 3
* open - “id”: 4  
* 
__[user-id]__ - userID tracking
