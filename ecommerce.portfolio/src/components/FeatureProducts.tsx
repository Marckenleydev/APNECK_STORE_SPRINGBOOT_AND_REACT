
import { Link } from 'react-router-dom'

import { productAPI } from '../services/ProductService';
import Loader from './Loader';


const FeaturedProducts = () => {
    const { data: productData, isLoading } = productAPI.useFetchProductsQuery();
    if(isLoading || !productData) {
      return (<h2 className='d-flex justify-content-center '><Loader/></h2>) 
    }
  return <>
  
     <div  className="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4 p-3">
      {productData?.slice(1, 5).map((product,index) => (
        <Link to={`/details/${product.referenceId}`}>
        <div key={index} className="col mb-5">
        <div  className="card h-100 m-auto">
          <img src={product.imageUrls[0]} className="card-img-top img-fluid" alt="..." />
          <div className="card-body">
            <p className="card-text mb-2">{product.brand}</p>
            <h5 className='mb-3'>{product.name.slice(0,18)}... </h5>
            <div className="card-footer m-auto text-center">
            <p className='text-danger fs-4'>{product.status}</p>
            <p className="price"><span className="red"></span> <strike>${product.rate} </strike></p>
            </div>
            <div className="card-footer d-md-none">
                <div className="d-flex justify-content-between align-items-center">
                  <Link to='shop' className='m-auto'>View products</Link>
                </div>
              </div>

          </div>
          
        </div>
        </div>
        </Link>
        ))}
      
     
    </div>

  </>;
}

export default FeaturedProducts