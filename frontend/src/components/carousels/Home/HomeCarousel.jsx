import { useNavigate } from "react-router-dom";
import HomeCard from "../../cards/HomeCard/HomeCard";

export default function HomeCarousel({ id }) {
    let navigate = useNavigate();
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
            header: "Ingresá tu ubicacion",
            img: "https://web.archive.org/web/20250211160640im_/https://http2.mlstatic.com/frontend-assets/homes-palpatine/dynamic-access-desktop/location.svg",
            desc: "Consultá costos y tiempos de entrega",
            link: "/login",
            footer: "Ingresar ubicacion"
        },
        {
            header: "Medios de pago",
            img: "https://http2.mlstatic.com/frontend-assets/homes-palpatine/dynamic-access-desktop/payment-methods.svg",
            desc: "Pagá tus compras de forma rápida y segura",
            link: "/login",
            footer: "Conocer medios de pago"
        },
        {
            header: "Nuestras categorías",
            img: "https://http2.mlstatic.com/frontend-assets/homes-palpatine/dynamic-access-desktop/categories.svg",
            desc: "Encontrá celulares, ropa, inmuebles y más",
            link: "/categories",
            footer: "Ir a categorías"
        },
        {
            header: "Más vendidos",
            img: "https://http2.mlstatic.com/frontend-assets/homes-palpatine/dynamic-access-desktop/top-sale.svg",
            desc: "Explorá productos que son tendencia",
            link: "/sales",
            footer: "Ir a mas vendidos"
        }
    ]

    return (
        <div id={id} className={`carousel slide products-carousel-wrapper sm`} style={{ height: "300px" }}>
            <div className={`carousel-inner`} >
                <div className={`carousel-item active`}>
                    <div className={`d-flex py-1 gap-3`}>
                        {defaultCards.map((card, index) => (
                            <HomeCard
                                key={`home-carousel-${index}`}
                                data={card}
                                navigate={navigate}
                            />
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
}
