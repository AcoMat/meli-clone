import starIcon from '../../../assets/ui/star.svg'
import starFilledIcon from '../../../assets/ui/star-fill.svg'

export default function StarReview({ setRating, rating }) {
    const totalStars = 5;

    return (
        <div>
            {[...Array(totalStars)].map((_, index) => {
                const starNumber = index + 1;
                return (
                    <img
                        key={starNumber}
                        src={starNumber <= rating ? starFilledIcon : starIcon}
                        alt={starNumber <= rating ? "Filled Star" : "Empty Star"}
                        onClick={() => setRating(rating === starNumber ? 0 : starNumber)}
                        style={{
                            width: "2.5rem",
                            height: "2.5rem",
                            filter: rating === 0
                                ? "grayscale(100%) brightness(95%)"
                                : "invert(46%) sepia(91%) saturate(747%) hue-rotate(181deg) brightness(97%) contrast(97%)",
                            opacity: rating === 0 ? 0.5 : 1,
                            cursor: "pointer"
                        }}
                    />
                );
            })}
        </div>
    );
};