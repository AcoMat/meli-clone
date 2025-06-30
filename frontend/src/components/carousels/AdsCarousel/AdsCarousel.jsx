import "./AdsCarousel.css";

export default function AdsCarousel() {

    return (
        <div id="adscarousel" className="carousel slide" data-bs-ride="carousel" data-bs-interval="5000" style={{ marginBottom: "-100px", width: "100%" }}>
            <div className="carousel-indicators ad-carousel-btn ad-carousel-indicators">
                <button type="button" data-bs-target="#adscarousel" data-bs-slide-to="0" className="active" aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#adscarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#adscarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
            </div>
            <div className="carousel-inner">
                <div className="carousel-item active">
                    <img src="https://http2.mlstatic.com/D_NQ_692925-MLA82633762801_022025-OO.webp" className="d-block w-100" alt="..." />
                </div>
                <div className="carousel-item">
                    <img src="https://http2.mlstatic.com/D_NQ_707149-MLA82301099980_022025-OO.webp" className="d-block w-100" alt="..." />
                </div>
                <div className="carousel-item">
                    <img src="https://http2.mlstatic.com/D_NQ_614834-MLA82768242685_022025-OO.webp" className="d-block w-100" alt="..." />
                </div>
            </div>
            <button className="carousel-control-prev ad-carousel-btn" type="button" data-bs-target="#adscarousel" data-bs-slide="prev">
                <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                <span className="visually-hidden">Previous</span>
            </button>
            <button className="carousel-control-next ad-carousel-btn" type="button" data-bs-target="#adscarousel" data-bs-slide="next">
                <span className="carousel-control-next-icon" aria-hidden="true"></span>
                <span className="visually-hidden">Next</span>
            </button>
        </div>
    );
}