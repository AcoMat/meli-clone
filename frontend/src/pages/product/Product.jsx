import { useParams } from 'react-router-dom';
import ProductImages from "../../components/product/ProductImages/ProductImages";
import LoadingSwitch from '../../components/basic/LoadingSwitch/LoadingSwitch';
import ProductBuyV2 from '../../components/product/ProductBuy-v2/ProductBuyV2';
import InfoSectionV2 from '../../components/layout/InfoSection/InfoSectionV2';
import ProductDescription from '../../components/product/ProductDescription/ProductDescription';
import ProductQuestions from '../../components/product/ProductQuestions/ProductQuestions';
import ProductCharacteristics from '../../components/product/ProductCharacteristics/ProductCharacteristics';
import { useEffect } from 'react';


export default function Product() {
    const { idProduct } = useParams();

    useEffect(() => {
        window.scrollTo(0,0)
    },[])

    return (
        <LoadingSwitch status={loading}>
            <div className='content-wrapper d-flex flex-column my-3 bg-body rounded shadow-sm px-4 pt-3'>
                <div className='d-flex w-100'>
                    <ProductImages images={product?.pictures} />
                    <ProductBuyV2 product={product} userId={user?.id} />
                </div>
                <InfoSectionV2 title="Características del producto">
                    <ProductCharacteristics characteristics={product?.attributes} />
                </InfoSectionV2>

                <InfoSectionV2 title="Descripción">
                    <ProductDescription description={product?.description} />
                </InfoSectionV2>

                <InfoSectionV2 title="Preguntas">
                    <ProductQuestions productId={product?.id} productQuestions={product?.comments} addQuestion={addComment} />
                </InfoSectionV2>

                {/* {TODO: RESEÑAS} */}
            </div>
        </LoadingSwitch>
    );
}