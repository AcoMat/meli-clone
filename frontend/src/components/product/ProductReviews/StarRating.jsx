import starIcon from '../../../assets/ui/star.svg'
import starFilledIcon from '../../../assets/ui/star-fill.svg'

export default function StarRating ({ rating, alt }) {
  const totalStars = 5;
  const filledStars = Math.round(rating);

  return (
    <div>
      {[...Array(totalStars)].map((_, index) => {
        const starNumber = index + 1;
        return (
          <img
            key={starNumber}
            src={starNumber <= filledStars ? starFilledIcon : starIcon}
            alt={starNumber <= filledStars ? "Filled Star" : "Empty Star"}
            style={{
              width: alt ? "1.5rem" : "0.9rem",
              height: alt ? "1.5rem" : "0.9rem",
              filter: "invert(46%) sepia(91%) saturate(747%) hue-rotate(181deg) brightness(97%) contrast(97%)"
            }}
          />
        );
      })}
    </div>
  );
};