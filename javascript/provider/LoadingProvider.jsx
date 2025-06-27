import React, { createContext, useContext, useState } from "react";

// 로딩 Context 생성
const LoadingContext = createContext();

// 로딩 상태 관리 Provider
export const LoadingProvider = ({ children }) => {
    const [loading, setLoading] = useState(false);

    const startLoading = () => {
        setLoading(true)
    };
    const stopLoading = () => setLoading(false);
    
    const styles = { //임시 로딩 스타일
        loadingWrap: {
            width: "100vw",
            height: "100vh",
            display: loading ? "flex" : "none", 
            alignItems: "center",
            justifyContent: "center",
            backgroundColor: "#00000050",
            zIndex: 100,
            position: "fixed",
            top:0
        },
        loadingImg: {
            width: "80px",
            height: "80px",
        },
    };
    
    return (
        <LoadingContext.Provider value={{ loading, startLoading, stopLoading }}>
            {children}
            {<div style={styles.loadingWrap} id="loadingWrap">
                <img alt="로딩 아이콘" style={styles.loadingImg} src="/image/loading.gif"/>
            </div>}
        </LoadingContext.Provider>
    );
};

// 로딩 상태를 사용할 Hook
export const useLoading = () => useContext(LoadingContext);
