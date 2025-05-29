import { useNavigate } from "react-router-dom";
import HomeCard from "../../cards/HomeCard/HomeCard";
import nextarrow from "../../../assets/arrows/next-arrow.svg";
import prevarrow from "../../../assets/arrows/prev-arrow.svg";

export default function HomeCarousel({ id }) {
    let navigate = useNavigate();
    const CARDS_PER_SLIDE = 3;
    const defaultCards = [
        {
            header: "Envio Gratis",
            img: "https://web.archive.org/web/20250211160640im_/https://http2.mlstatic.com/frontend-assets/homes-palpatine/dynamic-access-desktop/new-buyer.svg",
            desc: "Explora productos seleccionados con envío gratis",
            link: "/sales",
            footer: "Mostrar productos"
        },
        {
            header: "Ingresá a tu cuenta",
            img: "https://http2.mlstatic.com/frontend-assets/homes-palpatine/dynamic-access-desktop/registration-da.svg",
            desc: "Disfrutá de ofertas y comprá sin limites",
            link: "/login",
            footer: "Ingresar a tu cuenta"
        },
        {
            header: "Más vendidos",
            img: "https://http2.mlstatic.com/frontend-assets/homes-palpatine/dynamic-access-desktop/top-sale.svg",
            desc: "Explorá productos que son tendencia",
            link: "/sales",
            footer: "Ir a mas vendidos"
        }
    ]


    const createProductChunks = () => {
        const chunks = [];
        for (let i = 0; i < defaultCards?.length; i += CARDS_PER_SLIDE) {
            chunks.push(defaultCards?.slice(i, i + CARDS_PER_SLIDE));
        }
        return chunks;
    };

    const productChunks = createProductChunks();

    return (
        <div>
            <div id={id} className={`carousel slide products-carousel-wrapper`}>
                <div className={`carousel-inner mx-auto w-50`}>
                    {
                        productChunks.length > 0 ?
                            (productChunks.map((chunk, chunkIndex) => (
                                <div key={chunkIndex} className={`carousel-item ${chunkIndex === 0 ? 'active' : ''}`}>
                                    <div className={`d-flex py-1 gap-3 justify-content-center`}>
                                        {chunk.map((product, index) => (
                                            <HomeCard key={`product-${chunkIndex}-${index}`} data={product} navigate={navigate} />
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
