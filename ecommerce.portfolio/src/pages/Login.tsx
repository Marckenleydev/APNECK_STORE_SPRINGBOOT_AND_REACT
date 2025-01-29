
import React from 'react';
import { Link, Navigate, useLocation } from 'react-router-dom';
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Key } from '../enum/cache.key';
import { userAPI } from '../services/UserService';
import { IUserRequest } from '../models/ICredentials';



const Login = () => {
  const loginSchema = z.object({
    email: z
      .string()
      .min(2, "Email is required")
      .email("Please enter a valid email"),
    password: z
      .string()
      .min(5, "Password length should not be less than 5 characters"),
      
      
  });

  const location = useLocation();
  const isLoggedIn: boolean = (JSON.parse(localStorage.getItem(Key.LOGGEDIN)!) as boolean) || false;
  const [loginUser, {  error, isLoading, isSuccess }] = userAPI.useLoginUserMutation();
  const { register, handleSubmit, reset, formState: form,  } = useForm<IUserRequest>({
    resolver: zodResolver(loginSchema),
    mode: "onTouched", // Set default value here
  });



  const handleLogin = (credentials: IUserRequest) =>{
    loginUser(credentials);
    reset();
  } 

  React.useEffect(() => {
    window.scrollTo(0, 0);
    if (isSuccess) {
      localStorage.setItem(Key.LOGGEDIN, "true");
      window.location.reload();
    }
  }, [isSuccess]);

    if (isLoggedIn) { return location?.state?.from?.pathname ? ( <Navigate to={location?.state?.from?.pathname} replace />) : (  <Navigate to={"/"} replace />);}
    if (isSuccess ) {
      localStorage.setItem(Key.LOGGEDIN, "true");
      // window.location.reload();
      return location?.state?.from?.pathname ?
       (  <Navigate to={location?.state?.from?.pathname} replace />) : (  <Navigate to={"/"} replace />);
      }

  return (
    <section className="login-wrapper p-5">
      <div className="container-xxl">
        <div className="row justify-content-center">
          <div className="col-lg-4 col-md-8 col-sm-10">
              <div className="card">
                <div className="card-body p-5">
                  <h2 className="text-center">LOGIN</h2>
                  <p className="text-center mb-4">Welcome Back!!</p>
                  <form onSubmit={handleSubmit(handleLogin)}>
                    <div className="mb-3">
                      <label htmlFor="email" className="form-label mb-3">
                        Enter Your Email address
                      </label>
                      <input
                        type="email"
                        className="form-control"
                        
                        placeholder="enter email here ..."
                        
                        
                        required
                        {...register("email")}
                      />
                    </div>
                    <div className="mb-3">
                      <label htmlFor="password" className="form-label mb-3">
                        Enter Your password
                      </label>
                      <input
                        type="password"
                        className="form-control"
                        id="password"
                        placeholder="enter password here..."
                        
                        {...register("password")}
                        required
                      />
                    </div>
                    {error && (
                      <div className="alert alert-danger">{form.errors.password?.message}</div>
                    )}
                    <div className="mb-3">
                      <Link to="/forgotpasword" className="form-link">
                        Forgot password?
                      </Link>
                    </div>
                    <div className="d-flex justify-content-between align-items-center mb-3">
                      <p className='m-0'>Dont have an account?</p>
                      <Link to="/signup" className="form-link">
                        Sign up
                      </Link>
                    </div>
                    <div className="d-grid gap-2">
                      <button type="submit">{isLoading ? "Loading":"Login"}</button>
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

export default Login;