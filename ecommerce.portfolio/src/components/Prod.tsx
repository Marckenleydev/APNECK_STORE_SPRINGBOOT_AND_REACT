/* eslint-disable react-hooks/rules-of-hooks */
/* eslint-disable no-unused-vars */

import ReactStars from "react-rating-stars-component";
import { Link } from 'react-router-dom';

import { productAPI } from '../services/ProductService';
import { Key } from "../enum/cache.key";
import { toastError } from "../services/ToastService";


const prod = (props) => {
    const {referenceId,id, name, price, imageUrls, brand} = props.data;
     const isLogin = localStorage.getItem(Key.LOGGEDIN) === "true";
    const {   refetch } = isLogin ? productAPI.useFetchUserCartQuery() : {refetch: null} ;
const [addProductToCart] = productAPI.useAddProductToCartMutation();

const handleAddProduct = async (productId: number) => {
  if(isLogin){
    await addProductToCart(  productId );
    refetch()
  }else{
   
    toastError('Please login to add products to cart')
  }

  
};
    
  return <>
 <div className="col mb-5">
            <Link to="#" key={id}  className="card h-100 m-auto">
            <img src={imageUrls[0]} className="card-img-top img-fluid" alt="..." />
              <div className="card-body">
              <p className="card-text mb-2">{brand}</p>
                <h5>{name} </h5>
                <ReactStars
                    count={5}
                    edit={false}
                    value={4}
                    size={24}
                    activeColor="#EA9D5A"
                />
                <div className="mb-3">
                <p className="price mb-2"><span className="red">{price} </span>&nbsp;  <strike>{price * 2}$</strike></p>
                <Link to={`/details/${referenceId}`} >
                <p className="text-center"><button className='fs-4' id='clear-cart'>View Details</button></p>
                </Link>
                </div>
               <div className="d-flex justify-content-center">
               <button 
                  onClick={() => {
                    handleAddProduct(id);
                    event.target.classList.toggle("red");
                  }}
                  className="myButton"
                >
                  Add To Cart
                 
                </button>
               
              
               </div>
              </div>
            </Link>

            
        </div>
  </>
}

export default prod