import starIcon from '../../../assets/ui/star.svg'
import starFilledIcon from '../../../assets/ui/star-fill.svg'
import './LikeStarSwitch.css'
import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { useUserContext } from '../../../context/UserContext';

function LikeStarSwitch({ productId }) {
    const navigate = useNavigate();
    const { user, like } = useUserContext();
    const [liked, setLiked] = useState(false);
    const [star, setstar] = useState(starIcon);

    useEffect(() => {
        user?.likedproducts?.find(likedProduct => likedProduct.id === productId) ? setLiked(true) : setLiked(false)
    }, [user])

    const handleLike = (e) => {
        if (user) {
            setLiked(!liked)
            like(productId)
        } else {
            navigate('/login')
        }
    }

    return (
        <div className='like-button bg-body border rounded-circle position-absolute top-0 end-0 lh-1 text-center p-2 m-2'>
            <img
                src={liked ? starFilledIcon : star}
                onClick={handleLike}
                className=''
                width={20}
                height={20}
            />
        </div>
    );
}

export default LikeStarSwitch;
