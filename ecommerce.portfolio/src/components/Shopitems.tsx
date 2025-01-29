
import Prod from './Prod';
import { productAPI } from '../services/ProductService';
import Loader from './Loader';


const Shopitems = () => {
  const { data: productData, isLoading } = productAPI.useFetchProductsQuery();

  if(isLoading) {
    return (<div className='d-flex justify-content-center '><Loader/></div>) 
  }
  return <>
  <div className="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4">
  {productData?.map((product) => (
    <Prod key={product.id} data={product}  />
  ))}
  
        </div>
  </>
}

export default Shopitems