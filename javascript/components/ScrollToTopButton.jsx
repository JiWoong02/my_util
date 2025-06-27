import React, { useEffect, useState } from 'react';
import FixedDiv from "./FixedDiv.jsx";

const ScrollToTopButton = () => {
    const [isVisible, setIsVisible] = useState(false);

    // 스크롤 이벤트 등록
    useEffect(() => {
        const toggleVisibility = () => {
            if (window.scrollY > 300) {
                setIsVisible(true);
            } else {
                setIsVisible(false);
            }
        };

        window.addEventListener('scroll', toggleVisibility);
        return () => window.removeEventListener('scroll', toggleVisibility);
    }, []);

    const scrollToTop = () => {
        window.scrollTo({
            top: 0,
            behavior: 'smooth'
        });
    };

    return (
        isVisible && (
            <FixedDiv
                bottom={40}
                right={40}
                element={
                    <button
                        onClick={scrollToTop}
                        style={{
                            width: '60px',
                            height: '60px',
                            borderRadius: '50%',
                            backgroundColor: '#ff3e4f',
                            color: 'white',
                            border: 'none',
                            boxShadow: '0 4px 8px rgba(0,0,0,0.2)',
                            cursor: 'pointer',
                            fontSize: '16px',
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                            padding: 0
                        }}
                    >
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            width="32"
                            height="32"
                            fill="white"
                            viewBox="0 0 24 24"
                        >
                            <path d="M12 4l-8 8h6v8h4v-8h6z" />
                        </svg>
                    </button>
                }
            />
        )
    );
};

export default ScrollToTopButton;
