import React from 'react';

const ToggleSwitch = ({ isOn, onToggle, title }) => {
    const trackStyle = {
        width: '50px',
        height: '25px',
        borderRadius: '25px',
        backgroundColor: isOn ? '#4f46e5' : '#ccc',
        display: 'flex',
        alignItems: 'center',
        padding: '3px',
        cursor: 'pointer',
        transition: 'background-color 0.3s ease',
        boxSizing: 'border-box',
    };

    const knobStyle = {
        width: '19px',
        height: '19px',
        borderRadius: '50%',
        backgroundColor: '#fff',
        boxShadow: '0 1px 3px rgba(0,0,0,0.3)',
        transform: isOn ? 'translateX(25px)' : 'translateX(0px)',
        transition: 'transform 0.3s ease',
    };

    const labelStyle = {
        fontSize: '14px',
        marginRight: '10px',
    };

    return (
        <div style={{ display: 'flex', alignItems: 'center' }}>
            <span style={labelStyle}>{title}</span>
            <div style={trackStyle} onClick={onToggle}>
                <div style={knobStyle} />
            </div>
        </div>
    );
};

export default ToggleSwitch;
