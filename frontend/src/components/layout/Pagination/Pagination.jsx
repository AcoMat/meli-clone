import { Link } from "react-router-dom";
import "./Pagination.css"

function Pagination({ page, setPage }) {

    return (
        <nav>
            {
                <div className="mx-auto d-flex justify-content-center my-5 paginator align-items-center">
                    {page > 1 && <Link onClick={() => setPage(page - 1)}>{"< Anterior"}</Link>}
                    <Link className="paginator-current-page">{page}</Link>
                    <Link onClick={() => setPage(page + 1)}>{"Siguiente >"}</Link>
                </div>
            }
        </nav>
    );
}

export default Pagination;