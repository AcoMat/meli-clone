import './ProductImages.css';
import loadingsvg from '../../../assets/ui/loading.svg';
import { useEffect, useState } from 'react';

function ProductImages({ images }) {
    const [currentImage, setCurrentImage] = useState(null);

    useEffect(() => {
        if (images) {
            setCurrentImage(images[0]);
        }
    }, [images]);

    return (
        <div className="col bg-body rounded d-flex" style={{ maxHeight: "500px" }}>
            <div className="p-1 d-flex flex-column gap-2 overflow-y-auto overflow-x-hidden align-items-center">
                {
                    images?.map((image) => (
                        <img src={image}
                            className={`thumbnail  ${currentImage === image ? 'selected' : ''}`}
                            alt="product image" key={image}
                            onMouseOver={() => { setCurrentImage(image); }}
                        />
                    ))
                }
            </div>
            {
                !currentImage ?
                    <div className='d-flex justify-content-center align-items-center w-100 h-100'>
                        <div className="spinner-border text-primary" role="status">
                            <span className="visually-hidden">Loading...</span>
                        </div>
                    </div>
                    : <img src={currentImage} className="current-image mx-auto" />
            }
        </div>
    );
}

export default ProductImages;