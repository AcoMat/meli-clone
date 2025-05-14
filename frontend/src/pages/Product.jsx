import { useParams } from 'react-router-dom';
import ProductImages from "../components/product/ProductImages/ProductImages";
import LoadingSwitch from '../components/basic/LoadingSwitch/LoadingSwitch';
import ProductBuyV2 from '../components/product/ProductBuy-v2/ProductBuyV2';
import InfoSectionV2 from '../components/layout/InfoSection/InfoSectionV2';
import ProductDescription from '../components/product/ProductDescription/ProductDescription';
import ProductQuestions from '../components/product/ProductQuestions/ProductQuestions';
import LargeProductsCarousel from '../components/carousels/Products/Large/LargeProductsCarousel';
import ProductCharacteristics from '../components/product/ProductCharacteristics/ProductCharacteristics';
import useProduct from '../hooks/useProduct';
import { useUserContext } from '../context/AuthContext';
import { useEffect } from 'react';
import useRelatedProducts from '../hooks/useRelatedProducts';


export default function Product() {
    const { idProduct } = useParams();
    const { product, loading, addQuestion } = useProduct(idProduct);
    const { relatedProducts } = useRelatedProducts(idProduct);
    const { user } = useUserContext();

    useEffect(() => {
        window.scrollTo(0,0)
    },[])

    return (
        <LoadingSwitch status={loading}>
            <div className='content-wrapper d-flex flex-column my-3 bg-body rounded shadow-sm px-4 pt-3'>
                <div className='d-flex w-100'>
                    <ProductImages images={product?.images} />
                    <ProductBuyV2 product={product} userId={user?.id} />
                </div>
                <InfoSectionV2 title="Productos relacionados">
                    <LargeProductsCarousel id="related" products={relatedProducts} large={true} borders={true} prxslide={4} />
                </InfoSectionV2>
                <InfoSectionV2 title="Características del producto">
                    <ProductCharacteristics characteristics={product?.characteristic} />
                </InfoSectionV2>

                <InfoSectionV2 title="Descripción">
                    <ProductDescription description={product?.description} />
                </InfoSectionV2>

                <InfoSectionV2 title="Preguntas">
                    <ProductQuestions productId={product?.id} productQuestions={product?.questions} addQuestion={addQuestion} />
                </InfoSectionV2>
            </div>
        </LoadingSwitch>
    );
}