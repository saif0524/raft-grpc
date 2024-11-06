FROM python:3.12.6
WORKDIR /app
COPY . /app
RUN pip install grpcio grpcio-tools
CMD ["python", "python_raft_node.py"]