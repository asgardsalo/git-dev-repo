output "instance_public_ip" {
  value = aws_eip.node_eip.public_ip
}