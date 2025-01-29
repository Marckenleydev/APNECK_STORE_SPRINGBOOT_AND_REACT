
import { RiDeleteBack2Line } from 'react-icons/ri'
import { productAPI } from '../services/ProductService';
const CartItem = ({ data, refetchCart }) => {

  const {productId,availableQuantity, productName, price, imageUrl, brand, quantity} = data;
  const [addProductToCart] = productAPI.useAddProductToCartMutation();
  const [removeProductFromCart] = productAPI.useRemoveProductToCartMutation();


  const handleAddProduct = async (productId: number) => {

    await addProductToCart( productId  );
    console.log(productId);
    refetchCart();
  };



  const handleRemoveProduct = async (productId: number) => {
   
    await removeProductFromCart(  productId  );
    refetchCart();
  };
    
  return <>


<div className="container card my-3">
      <div className="row g-3">
        <div className="col-12 col-md-5">
          <div className="p-3">
            <div className="cart-item-image m-auto">
              <img src={imageUrl} className="card-img-top img-fluid" alt="..." />
            </div>
          </div>
        </div>
        <div className="col-12 col-md-7">
          <div className="p-3">
            <h2>{productName}</h2>
            <p className="cart-item-id">Product Brand: <b className='text-center mb-1'>{brand}</b></p>
            <p className="cart-item-id">Product Price: <b className='text-center mb-1'>${price}</b></p>
            <p className="cart-item-id">Product Number: <b className='text-center mb-3'>{productId}</b></p>
            <p className="cart-item-id">Items in Stock: <b className='text-danger'>{availableQuantity}</b></p>
          </div>
          <div className="p-3 d-flex justify-content-between align-items-center">
            <div className="count-handler">
              <button className="btn btn-outline-secondary" onClick={()=>handleAddProduct(productId)}>+</button>
              {quantity}
              <button className="btn btn-outline-secondary" onClick={()=>handleRemoveProduct(productId)}>-</button>
            </div>
            <button className="btn btn-outline-danger" >
              <RiDeleteBack2Line />
            </button>
          </div>
          <div className="p-3">
            <input type="text" className="form-control" id="coupon" placeholder="Enter coupon code..." />
          </div>
        </div>
      </div>
    </div>
  </>;
}

export default CartItem