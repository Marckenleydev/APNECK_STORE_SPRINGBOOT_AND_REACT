import { Link } from 'react-router-dom';
import ReactStars from "react-stars";
import { PRODUCTS1 } from './data';

interface MoreProductsProps {
  image1: string;
  image2: string;
  image3: string;
  image4: string;
}

const Moreproducts: React.FC<MoreProductsProps> = ({ image1, image2, image3, image4 }) => {
  return (
    <section className="more-featured-products">
      <div className="container-xxl">
        <div className="row">
          <div className="text-center p-5">
            <h1>More products</h1>
            <p>You might also like these</p>
          </div>
          <div className="d-flex justify-content-between p-5 row">
            {PRODUCTS1.slice(3, 7).map((product, index) => {
              // Override the product images with the passed props
              const productImage = [image1, image2, image3, image4][index];
              return (
                <div key={product.id} className="col-3 mb-3">
                  <Link to='/view2' className="card align-items-center">
                    <img src={productImage} className="card-img-top img-fluid" alt={product.name} />
                    <div className="card-body me-auto">
                      <p className="card-text mb-2">{product.brand}</p>
                      <h5>{product.name}</h5>
                      <ReactStars
                        count={5}
                        edit={false}
                        value={4}
                        size={24}
                        activeColor="#EA9D5A"
                      />
                      <p className="price">
                        <span className="red">{product.price} </span>&nbsp; <s>$200</s>
                      </p>
                    </div>
                    <div className="action-bar position-absolute">
                      <div className="d-flex align-items-center">
                        <Link to='/view2' id='button-linker' className='mb-3'>View</Link>
                      </div>
                    </div>
                  </Link>
                </div>
              );
            })}
          </div>
        </div>
      </div>
    </section>
  );
};

export default Moreproducts;