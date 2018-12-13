Steps to activate ssl certificate:

1. Request ssl certificate for namecheap
2. When order is delivered, click on activate
3. In order to activate, you need csr code which you either get from namecheap or you can generate for your own server
4. If you do not find the server you can click on generate csr using  decoder.link/csr_generator
5. Keep the private key secured for csr
6. Enter csr for activating ssl, enter company name, domain name, choose server type(tomcat chosen from apache nginx, cpanel n other/microsoft iis, tomcat)
7. Choose DCV (Domain Control Validation) method. There are 3 methods available: Email, HTTP-based, DNS-based
9. Chosen method is DNS-based . ENter address, company details, email.
10. In order to validate your domain, go to your amazon hosted zones
11. Create a new record set of type CNAME and provide respective HOST and TARGET which you got while activating SSL using DNS validation.
12. While importing certificate on AWS, go under Certification Manager, import certificate. Then under certificate body paste the certificate body that you received in zip file on your email.
13. Similarly, from the zip file, paste Certification private key and certificate chain.
