import React from "react";


const Popup = ({popupVisible, setPopupVisible,contentElement}) => {
    const bgStyle = {
        position: "absolute",
        top: 0,
        left: 0,
        width: "100%",
        height: "100%",
        zIndex: 1
    }

    return (<>
            <div id="popup" className="popup" style={{display: popupVisible ? 'flex' : 'none'}}>
                <div className="popup-bg" style={bgStyle} onClick={() => setPopupVisible(false)}></div>
                <div className="popup-content" style={{zIndex : 2}} >
                    {contentElement}
                </div>
            </div>
        </>
    )
}

export default Popup;