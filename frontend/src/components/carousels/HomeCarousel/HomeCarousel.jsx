import {useNavigate} from "react-router-dom";
import HomeCard from "../../cards/HomeCard/HomeCard";
import nextarrow from "../../../assets/arrows/next-arrow.svg";
import prevarrow from "../../../assets/arrows/prev-arrow.svg";
import {useEffect, useState} from "react";

export default function HomeCarousel() {
    let navigate = useNavigate();
    const [cardsPerSlide, setCardsPerSlide] = useState(3);
    useEffect(() => {
        const handleResize = () => {
            if (window.innerWidth < 576) {
                setCardsPerSlide(2);
            } else if (window.innerWidth < 992) {
                setCardsPerSlide(3);
            } else {
                setCardsPerSlide(5);
            }
        };
        handleResize();
        window.addEventListener('resize', handleResize);
        return () => window.removeEventListener('resize', handleResize);
    }, []);
    const defaultCards = [
        {
            header: "Ingresá a tu cuenta",
            img: "https://http2.mlstatic.com/frontend-assets/homes-palpatine/dynamic-access-desktop/registration-da.svg",
            desc: "Disfrutá de ofertas y comprá sin limites",
            link: "/login",
            footer: "Ingresar a tu cuenta"
        },
        {
            header: "Medios de pago",
            img: "https://http2.mlstatic.com/frontend-assets/homes-palpatine/dynamic-access-desktop/payment-methods.svg",
            desc: "Pagá tus compras de forma rápida y segura",
            footer: "Conocer medios de pago"
        },{
            header: "Tiendas oficiales",
            img: "https://http2.mlstatic.com/frontend-assets/homes-palpatine/dynamic-access-desktop/payment-methods.svg",
            desc: "Encontrá tus marcas preferidas",
            footer: "Mostrar tiendas"
        },
        {
            header: "Más vendidos",
            img: "https://http2.mlstatic.com/frontend-assets/homes-palpatine/dynamic-access-desktop/top-sale.svg",
            desc: "Explorá productos que son tendencia",
            footer: "Ir a mas vendidos"
        }
    ]


    const createProductChunks = () => {
        const chunks = [];
        for (let i = 0; i < defaultCards?.length; i += cardsPerSlide) {
            chunks.push(defaultCards?.slice(i, i + cardsPerSlide));
        }
        return chunks;
    };

    const productChunks = createProductChunks();

    return (
        <div id="homecarousel" className={`carousel slide products-carousel-wrapper py-3`}>
            <div className={`carousel-inner mx-auto w-75`}>
                {
                    productChunks.length > 0 ?
                        (productChunks.map((chunk, chunkIndex) => (
                            <div key={chunkIndex} className={`carousel-item ${chunkIndex === 0 ? 'active' : ''}`}>
                                <div className={`d-flex py-1 gap-3 justify-content-center`}>
                                    {chunk.map((product, index) => (
                                        <HomeCard key={`product-${chunkIndex}-${index}`} data={product}
                                                  navigate={navigate}/>
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
                    <button className="carousel-control-prev" type="button" data-bs-target={`#homecarousel`}
                            data-bs-slide="prev">
                        <img src={prevarrow} alt="Previous"/>
                    </button>
                    <button className="carousel-control-next" type="button" data-bs-target={`#homecarousel`}
                            data-bs-slide="next">
                        <img src={nextarrow} alt="Next"/>
                    </button>
                </>
            )}
        </div>
    );
}
