import React from "react";

const FixedDiv = ({bottom,right,element}) => {
    return (<div style={{
        position: 'fixed',
        bottom: `${bottom}px`,
        right: `${right}px`,
        zIndex: 1000
    }}>
        {element}
    </div>)
}

export default FixedDiv;