import SmallProductCard from "../../cards/SmallProductCard/SmallProductCard";
import "./ProductsCarousel.css";
import nextarrow from "../../../assets/arrows/next-arrow.svg";
import prevarrow from "../../../assets/arrows/prev-arrow.svg";
import { useNavigate } from "react-router-dom";

export default function ProductsCarousel({ id, title, products, prxslide, large }) {
    let navigate = useNavigate()
    const PRODUCTS_PER_SLIDE = prxslide || 6;

    const createProductChunks = () => {
        const chunks = [];
        for (let i = 0; i < products?.length; i += PRODUCTS_PER_SLIDE) {
            chunks.push(products?.slice(i, i + PRODUCTS_PER_SLIDE));
        }
        return chunks;
    };

    const productChunks = createProductChunks();

    return (
        <div className={`${title ? "bg-body rounded shadow-sm" : ""}`}>
            {title && <h2 className="products-carousel-title">{title}</h2>}
            <div id={id} className={`carousel slide products-carousel-wrapper ${!large ? "sm" : "md"}`}>
                <div className={`carousel-inner`}>
                    {
                        productChunks.length > 0 ?
                            (productChunks.map((chunk, chunkIndex) => (
                                <div key={chunkIndex} className={`carousel-item ${chunkIndex === 0 ? 'active' : ''}`}>
                                    <div className={`d-flex py-1 ${title ? "" : "gap-3"}`}>
                                        {chunk.map((product, index) => (
                                            <SmallProductCard
                                                key={`product-${chunkIndex}-${index}`}
                                                product={product}
                                                clickfnc={navigate}
                                            />
                                        ))}
                                    </div>
                                </div>
                            )))
                        :
                        <div className="d-flex justify-content-center align-items-center w-100 h-75">
                            <div className="spinner-border text-secondary" role="status">
                                <span className="visually-hidden">Loading...</span>
                            </div>
                        </div>

                    }
                </div>
                {productChunks.length > 1 && (
                    <>
                        <button className="carousel-control-prev" type="button" data-bs-target={`#${id}`} data-bs-slide="prev">
                            <img src={prevarrow} alt="Previous" />
                        </button>
                        <button className="carousel-control-next" type="button" data-bs-target={`#${id}`} data-bs-slide="next">
                            <img src={nextarrow} alt="Next" />
                        </button>
                    </>
                )}
            </div>
        </div>
    );
}
