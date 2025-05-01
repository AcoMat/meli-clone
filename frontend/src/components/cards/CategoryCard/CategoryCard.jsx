import "./CategoryCard.css"
import { useNavigate } from "react-router-dom";

function CategoryCard({ name, icon, redirect, id }) {
    const navigate = useNavigate();

    function handleClick() {
        navigate(`/categories/${id}`);
      }

    return (  
        <div className="category-card col" onClick={handleClick}> 
            <img src={icon} alt={name} className="icon my-3 w-25"/>
            <p className="text-capitalize">{name}</p>
        </div>
    );
}

export default CategoryCard;