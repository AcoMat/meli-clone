import { useNavigate } from "react-router-dom"
import ProductCard from "../../cards/ProductCard/ProductCard"
import notFound from '../../../assets/ui/404.svg';

export default function SearchResults({ title, productsPage }) {
    let navigate = useNavigate();

    return (
        <div className="d-flex w-100">
            <div className="d-flex flex-column w-25 my-4 ps-3">
                <div>
                    <h5 className="text-capitalize">{title?.replace("-", " ")}</h5>
                    <span>{productsPage ? productsPage.pagging?.total : 0} resultados</span>
                </div>
            </div>
            <div className="w-75 d-flex flex-column align-items-center">
                <div className="row gx-4 gy-4 w-100">
                    {productsPage?.results && productsPage?.results.length > 0 ?
                        productsPage?.results.map((product) => (
                            <div className="col-md-4 col-sm-6" key={product.id}>
                                <ProductCard
                                    product={product}
                                    clickfnc={navigate}
                                />
                            </div>
                        ))
                        :
                        <div className="d-flex flex-column align-items-center pt-5">
                            <img className="w-25 mx-auto" src={notFound} alt="no results" />
                            <h4 className="m-4">No se encontraron resultados</h4>
                            <a href="/">Ir a página principal</a>
                        </div>
                    }
                </div>
            </div>
        </div>
    )
}