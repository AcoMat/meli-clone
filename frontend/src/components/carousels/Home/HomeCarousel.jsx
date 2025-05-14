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
                    <div className={`d-flex py-1 gap-5 w-75 h-100 mx-auto justify-content-center align-items-center`}>
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
