
import { Link, useNavigate } from 'react-router-dom';
import { userAPI } from '../services/UserService';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';
import { IRegisterRequest } from '../models/ICredentials';
import { useForm } from 'react-hook-form';


const Signup = () => {
  const navigate = useNavigate();
  // Define the Zod schema for registration
  const registerSchema = z
  .object({
    firstName: z.string().min(2, 'First Name is required'),
    lastName: z.string().min(2, 'Last Name is required'),
    email: z.string().email('Please enter a valid email'),
    password: z
      .string()
      .min(5, 'Password length should not be less than 5 characters'),
    confirmPassword: z
      .string()
      .min(5, 'Confirm Password is required'),
    // Address fields validation
    address: z.object({
      street: z.string().min(2, 'Street name is required'),
      houseNumber: z.string().min(1, 'House Number is required'),
      zipCode: z.coerce.number().min(10000, 'Zip Code should be at least 5 digits'),
    }),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords don't match",
    path: ['confirmPassword'],
  });


  const [registerUser, { isLoading, isError, isSuccess, error: serverError }] = userAPI.useRegisterUserMutation();
  
  const { register, handleSubmit, reset, formState: { errors } } = useForm<IRegisterRequest>({
    resolver: zodResolver(registerSchema),
    mode: 'onTouched',
  });

  const handleRegisterSubmit = (credentials: IRegisterRequest) => {
    console.log('Form submitted with credentials:', credentials); 
    registerUser(credentials);
    reset();
  };
  if(isSuccess ){
    navigate('/login');
   

  }

  return (
    <section className="login-wrapper p-5">
      <div className="container-xxl">
        <div className="row justify-content-center">
          <div className="col-lg-4 col-md-8 col-sm-10">
            <div className="card">
              <div className="card-body p-5">
                <h2 className="text-center">Sign Up</h2>
                <form onSubmit={handleSubmit(handleRegisterSubmit)}>
                  {/* First Name */}
                  <div className="mb-3">
                    <label htmlFor="firstName" className="form-label mb-3">
                      Enter Your First Name
                    </label>
                    <input
                      type="text"
                      className="form-control"
                      placeholder="Enter first name here ..."
                      {...register('firstName')}
                    />
                    {errors.firstName && (
                      <div className="alert alert-danger">
                        {errors.firstName.message}
                      </div>
                    )}
                  </div>

                  {/* Last Name */}
                  <div className="mb-3">
                    <label htmlFor="lastName" className="form-label mb-3">
                      Enter Your Last Name
                    </label>
                    <input
                      type="text"
                      className="form-control"
                      placeholder="Enter last name here ..."
                      {...register('lastName')}
                    />
                    {errors.lastName && (
                      <div className="alert alert-danger">
                        {errors.lastName.message}
                      </div>
                    )}
                  </div>

                  {/* Email */}
                  <div className="mb-3">
                    <label htmlFor="email" className="form-label mb-3">
                      Enter Your Email address
                    </label>
                    <input
                      type="email"
                      className="form-control"
                      placeholder="Enter email here ..."
                      {...register('email')}
                    />
                    {errors.email && (
                      <div className="alert alert-danger">
                        {errors.email.message}
                      </div>
                    )}
                  </div>

                  {/* Password */}
                  <div className="mb-3">
                    <label htmlFor="password" className="form-label mb-3">
                      Enter Your Password
                    </label>
                    <input
                      type="password"
                      className="form-control"
                      id="password"
                      placeholder="Enter password here ..."
                      {...register('password')}
                    />
                    {errors.password && (
                      <div className="alert alert-danger">
                        {errors.password.message}
                      </div>
                    )}
                  </div>

                  {/* Confirm Password */}
                  <div className="mb-3">
                    <label htmlFor="confirmPassword" className="form-label mb-3">
                      Confirm Your Password
                    </label>
                    <input
                      type="password"
                      className="form-control"
                      id="confirmPassword"
                      placeholder="Re-enter password..."
                      {...register('confirmPassword')}
                    />
                    {errors.confirmPassword && (
                      <div className="alert alert-danger">
                        {errors.confirmPassword.message}
                      </div>
                    )}
                  </div>

                  {/* Address - Street */}
                  <div className="mb-3">
                    <label htmlFor="street" className="form-label mb-3">
                      Street
                    </label>
                    <input
                      type="text"
                      className="form-control"
                      placeholder="Enter street name here ..."
                      {...register('address.street')}
                    />
                    {errors.address?.street && (
                      <div className="alert alert-danger">
                        {errors.address.street.message}
                      </div>
                    )}
                  </div>

                  {/* Address - House Number */}
                  <div className="mb-3">
                    <label htmlFor="houseNumber" className="form-label mb-3">
                      House Number
                    </label>
                    <input
                      type="text"
                      className="form-control"
                      placeholder="Enter house number here ..."
                      {...register('address.houseNumber')}
                    />
                    {errors.address?.houseNumber && (
                      <div className="alert alert-danger">
                        {errors.address.houseNumber.message}
                      </div>
                    )}
                  </div>

                  {/* Address - Zip Code */}
                  <div className="mb-3">
                    <label htmlFor="zipCode" className="form-label mb-3">
                      Zip Code
                    </label>
                    <input
                      type="number"
                      className="form-control"
                      placeholder="Enter zip code here ..."
                      {...register('address.zipCode')}
                    />
                    {errors.address?.zipCode && (
                      <div className="alert alert-danger">
                        {errors.address.zipCode.message}
                      </div>
                    )}
                  </div>

                  {/* Server Error */}
                  {isError && serverError && (
                    <div className="alert alert-danger">
                      {'An error occurred.'}
                    </div>
                  )}

                  <div className="d-flex justify-content-between align-items-center mb-3">
                    <p>Have an account?</p>
                    <Link to="/login" className="form-link">
                      Log In
                    </Link>
                  </div>

                  <div className="d-grid gap-2">
                    <button type="submit" className="btn btn-primary" disabled={isLoading}>
                      {isLoading ? 'Signing Up...' : 'Sign Up'}
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default Signup;
