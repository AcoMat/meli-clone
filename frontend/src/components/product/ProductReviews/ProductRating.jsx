import StarRating from "./StarRating";
import starFilledIcon from '../../../assets/ui/star-fill.svg'

export default function ProductRating({ reviews }) {
    const averageRating = reviews?.reduce((acc, review) => acc + review.rating, 0) / reviews.length;
    const ratingCount = reviews?.reduce((acc, review) => {
        if (review.rating === 5) {
            acc.fiveStar++;
        } else if (review.rating === 4) {
            acc.fourStar++;
        } else if (review.rating === 3) {
            acc.threeStar++;
        } else if (review.rating === 2) {
            acc.twoStar++;
        } else if (review.rating === 1) {
            acc.oneStar++;
        }
        return acc;
    }, { fiveStar: 0, fourStar: 0, threeStar: 0, twoStar: 0, oneStar: 0 });

    return (
        reviews && reviews?.length > 0 &&
        <div className="d-flex flex-column w-25">
            <div className="d-flex gap-3">
                <span className="fw-bold blue-meli" style={{ fontSize: "3.5rem" }}>{averageRating.toFixed(1)}</span>
                <div className="d-flex flex-column justify-content-center">
                    <StarRating rating={averageRating} alt={true} />
                    <span className="text-muted fw-light lh-sm" style={{ fontSize: "0.8rem" }}>{reviews.length} calificaciones</span>
                </div>
            </div>
            <div className="d-flex flex-column gap-2 mt-3">
                {
                    Object.entries(ratingCount).map(([key, value], index) => (
                        <div key={key} className="d-flex gap-2 align-items-center">
                            <div className="progress w-75" role="progressbar" aria-valuemin="0" aria-valuemax="100" style={{ height: "0.5rem" }}>
                                <div className="progress-bar bg-secondary" style={{ width: (value / reviews.length) * 100 + "%" }}></div>
                            </div>
                            <span className="text-muted ms-1" style={{fontSize: "0.9rem"}}>{5 - index}<img src={starFilledIcon} className="w-50 mx-1 opacity-25"/></span>
                        </div>
                    ))
                }
            </div>
        </div>
    )
}