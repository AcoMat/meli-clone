import StarRating from "./StarRating";

export default function ProductReviews({ reviews }) {

    return (
        <div className="d-flex flex-column gap-3 w-50 px-4">
            {
                reviews &&
                reviews?.map((review) => (
                    <div key={review.id} className="d-flex flex-column gap-2 pb-3">
                        <div className="d-flex gap-2 justify-content-between p-1" style={{ fontSize: "0.8rem" }}>
                            <StarRating rating={review.rating} />
                            <span className="text-muted">
                                {review.createdAt && new Date(review.createdAt).toLocaleDateString('es-AR', {
                                    day: 'numeric',
                                    month: 'short',
                                    year: 'numeric'
                                })}
                            </span>
                        </div>
                        <p>{review.comment}</p>
                    </div>
                ))
            }
        </div>
    )
}