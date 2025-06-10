import "./Pagination.css"

function Pagination({ page, setPage }) {

    return (
        <nav>
            {
                <div className="mx-auto d-flex justify-content-center my-5 paginator align-items-center">
                    {page > 1 && <a onClick={() => setPage(page - 1)}>{"< Anterior"}</a>}
                    <a className="paginator-current-page">{page}</a>
                    <a onClick={() => setPage(page + 1)}>{"Siguiente >"}</a>
                </div>
            }
        </nav>
    );
}

export default Pagination;