const Paging = ({ page, setPage, pageSize, finalPage }) => {
    const currentGroup = Math.floor((page - 1) / pageSize);
    const startPage = currentGroup * pageSize + 1;
    const endPage = Math.min(startPage + pageSize - 1, finalPage);

    const pages = [];
    for (let i = startPage; i <= endPage; i++) {
        pages.push(i);
    }

    const handlePrevGroup = () => {
        const newPage = Math.max(startPage - 1, 1);
        setPage(newPage);
    };

    const handleNextGroup = () => {
        const newPage = Math.min(endPage + 1, finalPage);
        setPage(newPage);
    };

    return (
        <div style={{ display: "flex", justifyContent: "center", gap: "10px" }}>
            {startPage > 1 && (
                <button onClick={handlePrevGroup}>&lt;&lt;</button>
            )}
            {pages.map((p) => (
                <button
                    key={p}
                    onClick={() => setPage(p)}
                    style={{
                        fontWeight: p === page ? "bold" : "normal",
                    }}
                >
                    {p}
                </button>
            ))}
            {endPage < finalPage && (
                <button onClick={handleNextGroup}>&gt;&gt;</button>
            )}
        </div>
    );
};

export default Paging;