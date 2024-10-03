provider "aws" {
  region = "us-east-1" # Replace with your preferred region
}

resource "aws_vpc" "main_vpc" {
  cidr_block = "10.0.0.0/16"
}

resource "aws_subnet" "main_subnet" {
  vpc_id     = aws_vpc.main_vpc.id
  cidr_block = "10.0.1.0/24"
}

resource "aws_internet_gateway" "main_igw" {
  vpc_id = aws_vpc.main_vpc.id
}

resource "aws_security_group" "allow_all" {
  name        = "allow_all"
  description = "Allow all inbound and outbound traffic"
  vpc_id      = aws_vpc.main_vpc.id

  ingress {
    from_port   = 0
    to_port     = 65535
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 65535
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_instance" "k8s_node" {
  ami           = "ami-0abcdef1234567890" # Replace with a Linux 9 AMI ID
  instance_type = "t2.medium"

  subnet_id              = aws_subnet.main_subnet.id
  security_groups        = [aws_security_group.allow_all.name]
  associate_public_ip_address = true

  user_data = <<-EOF
    #!/bin/bash
    yum update -y
    yum install -y httpd
    systemctl enable httpd
    systemctl start httpd
    # Install Jenkins
    wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
    rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io.key
    yum install -y jenkins
    systemctl enable jenkins
    systemctl start jenkins
    # Install Kubernetes
    yum install -y kubeadm kubelet kubectl
    systemctl enable kubelet
    kubeadm init
    mkdir -p $HOME/.kube
    cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
    chown $(id -u):$(id -g) $HOME/.kube/config
    kubectl taint nodes --all node-role.kubernetes.io/master-
    kubectl apply -f https://docs.projectcalico.org/manifests/calico.yaml # Calico for networking
  EOF

  tags = {
    Name = "k8s-node"
  }
}

resource "aws_eip" "node_eip" {
  instance = aws_instance.k8s_node.id
}