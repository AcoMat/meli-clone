import "bootstrap/dist/css/bootstrap.min.css";

const NoResults = () => {
    return (
        <div className="bg-body w-75 mx-auto rounded p-5 d-flex align-items-center gap-5 justify-content-center fw-light">
            <img
                src="https://cdn-icons-png.flaticon.com/512/751/751463.png"
                alt="No results"
                width={70}
            />
            <div>
                <h5 className="fw-bold">No hay publicaciones que coincidan con tu búsqueda</h5>
                <ul className="text-start mt-3">
                    <li>
                        <strong>Revisá la ortografía</strong> de la palabra.
                    </li>
                    <li>
                        Utilizá <strong>palabras más genéricas</strong> o menos palabras.
                    </li>
                    <li>
                        <a href="/categories">Navegá por las categorías</a> para encontrar un producto similar.
                    </li>
                </ul>
            </div>
        </div>
    );
};

export default NoResults;
