import { useEffect } from "react";
import "./Pagination.css"

function Pagination({ page, setPage, totalPages }) {

    return (
        <nav>
            {
                totalPages > 1 &&
                <div className="mx-auto d-flex justify-content-center my-5 paginator align-items-center">
                    {
                        totalPages < 10 ?
                            Array.from({ length: totalPages }, (_, i) => i + 1).map((p) => (
                                <a key={p} className={page === p ? 'paginator-current-page' : ''} onClick={() => setPage(p)}>{p}</a>
                            ))
                            : page > totalPages - 8 ?
                                <>
                                    {page > 1 && <a onClick={() => setPage(page - 1)}>{"< Anterior"}</a>}
                                    <a className={page === 1 ? 'paginator-current-page' : ''} onClick={() => setPage(1)}>1</a>
                                    <span>...</span>
                                    {Array.from({ length: 8 }, (_, i) => i + 1).map((p) => (
                                        <a key={p} className={page === totalPages - 8 + p ? 'paginator-current-page' : ''} onClick={() => setPage(totalPages - 8 + p)}>{totalPages - 8 + p}</a>
                                    ))}
                                    {page < totalPages && <a onClick={() => setPage(page + 1)}>{"Siguiente >"}</a>}
                                </>
                                : page < 8 ?
                                    <>
                                        {page > 1 && <a onClick={() => setPage(page - 1)}>{"< Anterior"}</a>}

                                        {Array.from({ length: 10 }, (_, i) => i + 1).map((p) => (
                                            <a key={p} className={page === p ? 'paginator-current-page' : ''} onClick={() => setPage(p)}>{p}</a>
                                        ))}
                                        <span>...</span>
                                        <a className={page === totalPages ? 'paginator-current-page' : ''} onClick={() => setPage(totalPages)}>{totalPages}</a>
                                        {page < totalPages && <a onClick={() => setPage(page + 1)}>{"Siguiente >"}</a>}
                                    </>
                                    :
                                    <>
                                        {page > 1 && <a onClick={() => setPage(page - 1)}>{"< Anterior"}</a>}

                                        <a className={page === 1 ? 'paginator-current-page' : ''} onClick={() => setPage(1)}>1</a>
                                        <span>...</span>
                                        {Array.from({ length: 5 }, (_, i) => i + 1).map((p) => (
                                            <a key={p} className={page === page - 6 + p ? 'paginator-current-page' : ''} onClick={() => setPage(page - 6 + p)}>{page - 6 + p}</a>
                                        ))}
                                        <a className="paginator-current-page">{page}</a>
                                        {Array.from({ length: 5 }, (_, i) => i + 1).map((p) => (
                                            <a key={p} className={page === page + p ? 'paginator-current-page' : ''} onClick={() => setPage(page + p)}>{page + p}</a>
                                        ))}
                                        <span>...</span>
                                        <a className={page === totalPages ? 'paginator-current-page' : ''} onClick={() => setPage(totalPages)}>{totalPages}</a>
                                        {page < totalPages && <a onClick={() => setPage(page + 1)}>{"Siguiente >"}</a>}
                                    </>
                    }
                </div>
            }
        </nav>
    );
}

export default Pagination;