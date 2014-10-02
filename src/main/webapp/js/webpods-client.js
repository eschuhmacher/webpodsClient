function Client(url, debug, debugMessageBox) {

	var pvIDIndex = 0;
	var pvArray = [];
	var websocket = null;
	var webSocketOnOpenCallbacks = [];
	var webSocketOnCloseCallbacks = [];
	var webSocketOnErrorCallbacks = [];
	var onServerMessageCallbacks = [];
	var clientSelf = this;
	var debug = debug;
	var debugMessageBox = debugMessageBox;
	var defaultTypeVersion = 1;

	openWebSocket(url);

    /**
	 * Add a callback to WebSocket onOpen event.
	 * @param {Client~WebSocketEventCallback} callback the callback function on WebSocket open event.
	 */
	this.addWebSocketOnOpenCallback = function(callback) {
		webSocketOnOpenCallbacks.push(callback);
	};

	/**
	 * Remove a WebSocket onOpen callback.
	 * @param {Client~WebSocketEventCallback} callback the callback function on WebSocket open event.
	 */
	this.removeWebSocketOnOpenCallback = function(callback){
		webSocketOnOpenCallbacks.splice(webSocketOnOpenCallbacks.indexOf(callback), 1);
	};


	/**
	 * Add a callback to WebSocket onClose event.
	 * @param {Client~WebSocketEventCallback} callback the callback function on WebSocket close event.
	 *
	 */
	this.addWebSocketOnCloseCallback = function(callback) {
		webSocketOnCloseCallbacks.push(callback);
	};



	/**
	 * Add a callback to WebSocket onError event.
	 * @param {Client~WebSocketEventCallback} callback the callback function on WebSocket error event.
	 *
	 */
	this.addWebSocketOnErrorCallback = function(callback) {
		webSocketOnErrorCallbacks.push(callback);
	};

	/**A callback function that will be notified when there is a message from server.
	 * @callback Client~OnServerMessageCallback
	 *
	 */

	/**
	 * Add a callback that will be notified when there is a notification message from server.
	 * @param {Client~OnServerMessageCallback} callback the callback
	 *
	 */
	this.addOnServerMessageCallback = function(callback) {
		onServerMessageCallbacks.push(callback);
	};

    this.subscribePV = function(name, readOnly, type, version, maxRate) {
        var typeJson;
        if (type != null) {
            if (version == null)
                version = defaultVersion;
            typeJson = JSON.stringify({
                "name" : type,
                "version" : version
            });

        }
        var json = JSON.stringify({
            "message" : "subscribe",
            "id" : pvIDIndex,
            "pv" : name,
            "readOnly" : readOnly,
            "maxRate" : maxRate,
            "type" : typeJson
        });
        var pv = new PV(name);
        pvArray[pvIDIndex] = pv;
        pv.id = pvIDIndex;
        pv.name = name;
        pv.readOnly = readOnly;
        pv.type = type;
        pv.connected = true;
        var clientSelf = this;
        clientSelf.sendText(json);
        pvIDIndex++;
        return pv;
    };

    function openWebSocket(url) {
        if ('WebSocket' in window) {
            websocket = new WebSocket(url, "org.client");
        } else if ('MozWebSocket' in window) {
            websocket = new MozWebSocket(url, "org.client");
        } else {
            throw new Error('WebSocket is not supported by this browser.');
        }
        websocket.binaryType = "arraybuffer";
        websocket.onopen = function(evt) {
            fireOnOpen(evt);
        };

        websocket.onmessage = function(evt) {
            var json;
            json = JSON.parse(evt.data);
            dispatchMessage(json);

        };

        websocket.onerror = function(evt) {
            fireOnError(evt);
        };

    }

    function writeToScreen(message) {
        if (debugMessageBox != null)
            document.getElementById(debugMessageBox).value = message;
    }


    /**
     * Close Websocket.
     */
    this.close = function() {
        if (websocket != null)
            websocket.close();
        websocket = null;
    };

    this.sendText =function(text) {
        websocket.send(text);
        if(debug)
            writeToScreen(text);
    };


    /**
     * Get the PV from its id.
     * @param {number} id id of the PV.
     * @returns {Client~PV} the PV.
     */
    this.getPV = function(id){
        return pvArray[id];
    };

    /**Get all PVs on this client.
     * @returns {Array.<Client~PV>} All PVs in an array.
     */
    this.getAllPVs = function() {
        return pvArray;
    };

    function dispatchMessage(json) {
        if (json.message != null)
            handleServerMessage(json);
        if (json.id != null) {
            if (pvArray[json.id] != null)
                pvArray[json.id].firePVEventFunc(json);
        }
    }

    function handleServerMessage(json) {
		if(json.type == "error")
			console.log("Error: " + json.error);
		fireOnServerMessage(json);
	}

	function processJsonForPV(json, PV) {
        switch (json.type) {
        case "connection":
            PV.connected = json.connected;
            PV.readOnly = !json.writeConnected;
            break;
        case "value":
            PV.value =  json.value;
            break;
        default:
            break;
        }
    };
    function fireOnOpen(evt) {
        for ( var i in webSocketOnOpenCallbacks) {
            webSocketOnOpenCallbacks[i](evt);
        }
    }

    function fireOnClose(evt) {
        for ( var i in webSocketOnCloseCallbacks) {
            webSocketOnCloseCallbacks[i](evt);
        }
    }


    function fireOnError(evt) {
        for ( var i in webSocketOnErrorCallbacks) {
            webSocketOnErrorCallbacks[i](evt);
        }
    }

    function fireOnServerMessage(json) {
        for ( var i in onServerMessageCallbacks) {
            onServerMessageCallbacks[i](json);
        }
    }

    function unsubscribe(pvIDIndex) {
		var json = JSON.stringify({
			"message" : "unsubscribe",
			"id" : pvIDIndex
		});
		clientSelf.sendText(json);
		delete pvArray[pvIDIndex];
	}

	function pausePV(pvIDIndex){
		var json = JSON.stringify({
			"message" : "pause",
			"id" : pvIDIndex,
		});
		clientSelf.sendText(json);
	}

	function resumePV(pvIDIndex){
		var json = JSON.stringify({
			"message" : "resume",
			"id" : pvIDIndex,
		});
		clientSelf.sendText(json);
	}

	function setPVValue(pvIDIndex, value){
		var json = JSON.stringify({
            "message" : "write",
			"id" : pvIDIndex,
			"value" : value
		});
		clientSelf.sendText(json);
	}

	function PV(name) {
        /**Name of the PV.
         * @type {string}
         */
        this.name = name;
        this.id = -1;
        this.value = null;
        this.pvCallbacks = [];
        this.paused = false;
        this.connected = false;
        this.readOnly = true;

    }
    /**If the pv is connected to the device.
     * @returns {boolean} true if the pv is connected.
     */
    PV.prototype.isConnected = function(){
        return this.connected;
    };

    /**
     * If write operation is allowed on the pv
     * @return {boolean}
     */
    PV.prototype.isWriteAllowed = function(){
        return !this.readOnly;
    };

    /**
     * If the pv is paused
     * @return {boolean}
     */
    PV.prototype.isPaused = function(){
        return this.paused;
    };

    /**
     * Get value of the PV.
     * return {object} the value which is a data structure depending on the PV.
     */
    PV.prototype.getValue = function(){
        return this.value;
    };
    /**
     * Add a callback to the PV that will be notified on PV's event.
     * @param {Client~PV~PVCallback} callback the callback function.
     */
    PV.prototype.addCallback = function(callback) {
        this.pvCallbacks.push(callback);
    };

    /**
     * Remove a callback.
     * @param {Client~PV~PVCallback} callback the callback function.
     */
    PV.prototype.removeCallback = function(callback) {
        for ( var i in this.pvCallbacks) {
            if (this.pvCallbacks[i] == callback)
                delete this.pvCallbacks[i];
        }
    };
    /**
     * Set pv value.
     * @param {object} value
     *        the value to be set. It must be a value type that the PV can accept,
     *        for example, a number for numeric PV.
     */
    PV.prototype.setValue = function(value) {
        setPVValue(this.id, value);
    };

    PV.prototype.updateValue = function() {
            setPVValue(this.id, this.value.value);
    };

    /**
     * unsubscribe pv .
     *
     */
    PV.prototype.unsubscribe = function() {
        unsubscribe(this.id);
    };

    PV.prototype.pause = function() {
        this.paused = true;
        pausePV(this.id);
    };

    PV.prototype.resume = function() {
        this.paused = false;
        resumePV(this.id);
    };


    PV.prototype.firePVEventFunc = function(json) {
        // update the  properties of the pv
        // processJson should be implemented in specific protocol library
        processJsonForPV(json, this);
        for ( var i in this.pvCallbacks) {
            this.pvCallbacks[i](json, this);
        }
    };
}