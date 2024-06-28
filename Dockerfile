# Use CentOS Stream 9 as base image
FROM centos:9

# Update packages and install necessary tools
RUN yum update -y && \
    yum install -y \
        wget \
        vim \
        net-tools \
        curl \
        httpd \
        mod_ssl \
        postfix \
        cyrus-sasl \
        cyrus-sasl-plain \
        cyrus-sasl-md5 \
        mailx
    yum clean all

# Optional: Set up additional configurations if needed
# For example, you can set up a non-root user
RUN useradd -m -s /bin/bash salo && \
    echo "salo ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers
RUN cp /home/ec2-user/.ssh/authorized_keys /home/salo/.ssh/.

# Set any environment variables if necessary

# Start your desired services or applications if needed

# Define default command to run when container starts
# CMD ["/bin/bash"]
